package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import reflection.pojo.GoodsItem;
import reflection.pojo.TwoBodCoeData;

public class Maintest {
    public static void main(String[] args) {
        List<GoodsItem> goodsItems = initPojo();
        reflect(goodsItems);
    }

    public static List<GoodsItem> initPojo() {
        ArrayList<GoodsItem> goodsItems = new ArrayList<>();
        for(Long i=0L; i<5L; i++) {
            GoodsItem good = new GoodsItem();
            good.setId(i);
            good.setIndexNbr(i.intValue() + 1);
            good.setCategory(i+"_category");
            good.setModel(i+"_model");
            good.setBrand(i+"_brand");
            good.setType(i+"_type");
            good.setPrice(BigDecimal.valueOf(i));
            goodsItems.add(good);
        }
        return goodsItems;
    }

    public static void reflect(List<GoodsItem> goodsItems) {
        TwoBodCoeData data = new TwoBodCoeData();
        goodsItems.stream().sorted(Comparator.comparing(GoodsItem::getIndexNbr)).forEach(goodsItem -> {
            Integer indexNbr = goodsItem.getIndexNbr();
            try {
                reflectGoods(data, goodsItem, indexNbr);
            } catch (Exception e) {
                System.out.println("@@@Exception:" + ExceptionUtils.getStackTrace(e));
            }
        });
        System.out.println("@@@data:" + data);
    }

    private static void reflectGoods(TwoBodCoeData data, GoodsItem goodsItem, Integer indexNbr) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        data.getClass().getDeclaredMethod("setGoods" + indexNbr + "Category", String.class).invoke(data,goodsItem.getCategory());
        data.getClass().getDeclaredMethod("setGoods" + indexNbr + "Model", String.class).invoke(data,goodsItem.getModel());
        data.getClass().getDeclaredMethod("setGoods" + indexNbr + "Brand", String.class).invoke(data,goodsItem.getBrand());
        data.getClass().getDeclaredMethod("setGoods" + indexNbr + "Type", String.class).invoke(data,goodsItem.getType());
        data.getClass().getDeclaredMethod("setGoods" + indexNbr + "Price", BigDecimal.class).invoke(data,goodsItem.getPrice());
    }
}
