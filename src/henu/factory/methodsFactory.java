package henu.factory;

import henu.dao.SelectMethods;
import henu.dao.impl.book.methods.*;

public class methodsFactory {
    public static SelectMethods getSelectMethod(String param)
    {
        switch (param){
            case "ISBN":
                return new getBookByISBN();
            case "name":
                return new getBookByName();
            case "type":
                return new getBookByType();
            case "writer":
                return new getBookByWriter();
            case "defaultBook":
                return new getBookDefault();
            case "defaultUser":
                return new getUserByDefault();
            case "username":
                return new getUserByUsername();
            case "gender":
                return new getUserByGender();
        }
        return null;
    }
}
