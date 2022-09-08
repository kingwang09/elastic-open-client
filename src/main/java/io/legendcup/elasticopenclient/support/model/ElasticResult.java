package io.legendcup.elasticopenclient.support.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;


@NoArgsConstructor
@Getter
public class ElasticResult<T> {
    private boolean isLast;
    private long total;
    private Collection<T> list;
    private Object[] cursor;

    @Builder
    public ElasticResult(final Object[] cursor, final boolean isLast, final Collection<T> list, final long total) {
        this.cursor = cursor;
        this.isLast = isLast;
        this.list = list;
        this.total = total;
    }

    public static <T> ElasticResult<T> empty() {
        return new ElasticResult<>();
    }
}