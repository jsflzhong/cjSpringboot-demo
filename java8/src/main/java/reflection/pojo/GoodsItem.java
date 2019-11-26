package reflection.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsItem {
    private Long id;

    private Long idApplication;

    private String category;

    private String type;

    private BigDecimal price;

    private String brand;

    private String brandTxt;

    private String model;

    private String modelTxt;

    private Integer indexNbr;

    private LocalDateTime cdate;

    private String creator;

    private LocalDateTime edate;

    private String editor;
}
