package henu.dao.impl.book;

import henu.bean.BookInfo;
import henu.bean.Reader;
import henu.dao.SelectMethods;
import henu.factory.methodsFactory;

import javax.servlet.http.HttpServletRequest;

public class bookSearch {
    public static BookInfo[] search(HttpServletRequest request) {

        String method = (String) request.getSession().getAttribute("method");
        String context = (String) request.getSession().getAttribute("context");
        BookInfo[] obj = null;
        if (method != null) {
            if (!context.equals("")) {
                SelectMethods m = methodsFactory.getSelectMethod(method);
                obj = (BookInfo[]) m.getBookInfo(context);
            } else {
                SelectMethods m = methodsFactory.getSelectMethod("defaultBook");
                obj = (BookInfo[]) m.getBookInfo("");
            }
        } else {
            SelectMethods m = methodsFactory.getSelectMethod("defaultBook");
            obj = (BookInfo[]) m.getBookInfo("");
        }
        return obj;
    }

    public static Reader[] searchUser(HttpServletRequest request) {

        String method = (String) request.getSession().getAttribute("method");
        String context = (String) request.getSession().getAttribute("context");
        Reader[] obj = null;
        if (method != null) {
            if (!context.equals("")) {
                SelectMethods m = methodsFactory.getSelectMethod(method);
                obj = (Reader[]) m.getBookInfo(context);
            } else {
                SelectMethods m = methodsFactory.getSelectMethod("defaultUser");
                obj = (Reader[]) m.getBookInfo("");
            }
        } else {
            SelectMethods m = methodsFactory.getSelectMethod("defaultUser");
            obj = (Reader[]) m.getBookInfo("");
        }
        return obj;
    }
}
