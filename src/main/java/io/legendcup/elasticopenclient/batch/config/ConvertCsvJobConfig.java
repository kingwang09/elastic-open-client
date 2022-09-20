package io.legendcup.elasticopenclient.batch.config;

import io.legendcup.elasticopenclient.batch.model.ConvertCsv;
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
public class ConvertCsvJobConfig {
    @Value("${batch.csv.converter.reader.path}")
    private String csvReaderPath;
    @Value("${batch.csv.converter.writer.path}")
    private String csvWriterPath;
    @Value("${batch.csv.converter.chunk-size}")
    private Integer chunkSize;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job convertCsvJob(){
        return jobBuilderFactory.get("convertCsvJob")
                .start(convertCsvStep())
                .build();
    }

    @Bean
    public Step convertCsvStep(){
        return stepBuilderFactory.get("convertCsvStep")
                .<ConvertCsv,ConvertCsv>chunk(chunkSize)
                .reader(convertCsvFileReader())
                .writer(convertCsvLogWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<ConvertCsv> convertCsvFileReader(){
        String[] fields = getFields(ConvertCsv.class);
        return new FlatFileItemReaderBuilder<ConvertCsv>()
                .name("convertCsvFileReader")
                .resource(new FileSystemResource(csvReaderPath))
                .strict(false)
                .encoding("UTF-8")
                .delimited()
                .delimiter(",")
                .quoteCharacter('"')
                .names(fields)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ConvertCsv>(){
                    {setTargetType(ConvertCsv.class);}
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
    public ItemWriter<ConvertCsv> convertCsvLogWriter() {
        return list -> {
            for (ConvertCsv result: list) {
                log.info("convert result={}", result);
            }
        };
    }
}
