package com.dzl.sport.util;

public class ProductUtil<E> {

    public E getSplitObject(E[] objs){
        String obj = "";
        if(objs!=null){
            int count = 0;
            for(E o:objs){
                count++;
                if(count==objs.length){
                    obj+=o;
                }else {
                    obj+=o+",";
                }
            }
        }
        return (E)obj;
    }
}
