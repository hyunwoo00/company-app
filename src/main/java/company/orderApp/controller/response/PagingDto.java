package company.orderApp.controller.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagingDto<T> {
    private long total;
    private T data;
    private double totalPage;
    private double limit;


    public PagingDto(long total, T data, double limit) {
        this.total = total;
        this.data = data;
        this.totalPage = Math.ceil((double) total / limit);
    }
}
