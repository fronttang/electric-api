/**
 * 
 */
package com.rosenzest.base;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * list返回
 * 
 * @author fronttang
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ListResult<T> extends Result<List<T>> {

    private static final long serialVersionUID = -4440218837817233216L;

    private Long totalNum = 0L;

    public static final <T> ListResult<T> SUCCESS(Long totalNum, List<T> data) {
        return new ListResult<T>(totalNum, data);
    }

    public ListResult(Long totalNum, List<T> data) {
        super();
        this.totalNum = totalNum;
        this.data = data;
    }

    public ListResult(Integer totalNum, List<T> data) {
        super();
        this.totalNum = Long.parseLong(totalNum + "");
        this.data = data;
    }

    public ListResult(int code, String msg) {
        super(code, msg);
    }

    public ListResult(IResult result) {
        super(result);
    }

    public ListResult() {}

}
