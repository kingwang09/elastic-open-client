package io.legendcup.elasticopenclient.batch.config;

import io.legendcup.elasticopenclient.batch.model.RawSampleCompany;
import io.legendcup.elasticopenclient.batch.model.SampleCompany;
import io.legendcup.elasticopenclient.batch.processor.CsvProcessor;
import io.legendcup.elasticopenclient.batch.writer.ElasticWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class CsvJobConfig {
    @Value("${batch.csv.reader.path}")
    private String csvReaderPath;
    @Value("${batch.csv.writer.path}")
    private String csvWriterPath;
    @Value("${batch.chunk-size}")
    private Integer chunkSize;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job basicJob(){
        return jobBuilderFactory.get("basicJob")
                .start(companyStep())
                .build();
    }

    @Bean
    public Step companyStep(){
        return stepBuilderFactory.get("companyStep")
                .<RawSampleCompany,SampleCompany>chunk(chunkSize)
                .reader(csvFileReader())
                .processor(csvProcessor())
                //.writer(csvFileWriter())
                //.writer(logWriter())
                .writer(elasticWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<RawSampleCompany> csvFileReader(){
        String[] fields = getFields(RawSampleCompany.class);
        return new FlatFileItemReaderBuilder<RawSampleCompany>()
                .name("csvFileReader")
                .resource(new FileSystemResource(csvReaderPath))
                .strict(false)
                .encoding("EUC-KR")
                .delimited()
                .delimiter("|")
                .quoteCharacter('╊')
                .names(fields)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<RawSampleCompany>(){
                    {setTargetType(RawSampleCompany.class);}
                })
                .build();
    }

    private String[] getFields(Class className){
        List<String> fieldList = new LinkedList<>();
        Field[] fields = className.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            fieldList.add(field.getName());
        }
        log.info("csv file fields={}", fieldList);
        String[] result = fieldList.toArray(new String[fieldList.size()]);
        return result;
    }

    @Bean
    public CsvProcessor csvProcessor(){
        return new CsvProcessor();
    }

    @Bean
    public FlatFileItemWriter<RawSampleCompany> csvFileWriter(){
        String[] fields = getFields(RawSampleCompany.class);
        return new FlatFileItemWriterBuilder<RawSampleCompany>()
                .name("csvFileWriter")
                .resource(new FileSystemResource(csvWriterPath))
                .encoding("EUC-KR")
                .delimited()
                .delimiter("|")
                .names(fields)
                .build();
    }

    @Bean
    public ItemWriter<RawSampleCompany> logWriter() {
        return list -> {
            for (RawSampleCompany result: list) {
                log.info("complete log={}", result);
            }
        };
    }

    @Bean
    public ItemWriter<SampleCompany> elasticWriter(){
        return new ElasticWriter();
    }
}
