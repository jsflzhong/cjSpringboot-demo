package lombok;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 心得: @Slf4j可用, 其他都不太好用. 因为@Data和@Setter都不支持返回this,而@Builder又有去掉了无参构造器的坑.
 *      除非单独写一个无参的构造器,并用@Tolerate注解在该方法头上, 否则报错.
 *      如果不写这个无参构造器, 导致无参构造器不存在, 可能会引发很多问题.
 *
 *lombok的注解介绍
 * @NonNull : 让你不在担忧并且爱上NullPointerException
 *
 * @CleanUp : 自动资源管理：不用再在finally中添加资源的close方法
 *
 * @Setter/@Getter : 自动生成set和get方法
 *
 * @ToString : 自动生成toString方法
 *
 * @EqualsAndHashcode : 从对象的字段中生成hashCode和equals的实现
 *
 * @NoArgsConstructor/@RequiredArgsConstructor/@AllArgsConstructor
 * 自动生成构造方法
 *
 * @Data : 自动生成set/get方法，toString方法，equals方法，hashCode方法，不带参数的构造方法
 *
 * @Value : 用于注解final类
 *
 * @Builder : 产生复杂的构建器api类
 *
 * @SneakyThrows : 异常处理（谨慎使用）
 *
 * @Synchronized : 同步方法安全的转化
 *
 * @Getter(lazy=true) :
 *
 * @Log : 支持各种logger对象，使用时用对应的注解，如：@Log4j
 *
 * 推荐使用技巧
 * 在 Bean / Entity 类上使用 @Data 注解。
 * 需要使用 Log 对象的地方使用 @Log4j（依项目日志框架决定）。
 * 注意：lombok 的注解不能被继承。
 *
 * IDEA中使用lombok
 * 如果想让lombok生效我们还需要针对idea工具进行插件的安装
 */
@Data
@Slf4j
public class TestLombok {

    private int id;
    private String name;

    public static void main(String[] args) {
    }

}
