package br.brunocatao.exemplobatch.config;

import br.brunocatao.exemplobatch.batch.OperacaoItemProcessor;
import br.brunocatao.exemplobatch.dao.OperacaoRepository;
import br.brunocatao.exemplobatch.entities.Operacao;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@AllArgsConstructor
public class BatchConfig {
  private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

  private OperacaoRepository operacaoRepository;

  private JobBuilderFactory jobBuilderFactory;
  private StepBuilderFactory stepBuilderFactory;
  private JobLauncher jobLauncher;

  @Bean
  public ItemReader<Operacao> reader() {
    return () -> {
      PageRequest pageRequest = PageRequest.of(0, 1);
      Page<Operacao> consulta = operacaoRepository.findNaoProcessadas(pageRequest);

      if (consulta.getTotalElements() == 0) {
        return null;
      }

      return consulta.getContent().get(0);
    };
  }

  @Bean
  public OperacaoItemProcessor processor() {
    return new OperacaoItemProcessor();
  }

  @Bean
  public ItemWriter<Operacao> writer() {
    return (operacoes) -> {
      operacoes.stream().forEach(op -> operacaoRepository.save(op));
    };
  }

  @Bean
  public JobExecutionListener listener() {
    return new JobExecutionListener() {
      @Override
      public void beforeJob(JobExecution jobExecution) {
        log.info("Vou iniciar o job");
      }

      @Override
      public void afterJob(JobExecution jobExecution) {
        log.info("Terminei o job");
      }
    };
  }

  @Bean
  public Step step() {
    return stepBuilderFactory.get("processaOperacaoStep")
        .<Operacao, Operacao> chunk(10)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean
  public Job processaOperacoesJob() {
    return jobBuilderFactory.get("processaOperacoesJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener())
        .flow(step())
        .end()
        .build();
  }

  @Scheduled(fixedRate = 10000)
  public void executaBatch() throws Exception {
    log.info("Verificando se tem jobs para executar...");
    JobParameters param = new JobParametersBuilder()
        .addString("JobID", String.valueOf(System.currentTimeMillis()))
        .toJobParameters();
    jobLauncher.run(processaOperacoesJob(), param);
  }
}
