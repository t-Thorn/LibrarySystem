package henu.dao;

import henu.dao.impl.book.pages.*;

import javax.servlet.http.HttpServletRequest;

public  class Pages {
    protected int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
    public static Pages getpage(HttpServletRequest request,int flag){
        Pages page_now=(Pages) request.getSession().getAttribute("page");//获取当前页
        if (page_now == null) {
            switch (flag){
                case 1:
                    page_now = new book();
                    break;
                case 3:
                    page_now = new RD();
                    break;
                case 4:
                    System.out.println("ok3");
                    page_now = new RB();
                    System.out.println("ok4");
                    break;
                case 2:
                    page_now = new user();
                    break;
            }
            page_now.setFlag(0);
            request.getSession().setAttribute("page", page_now);
        } else {
            switch (flag){
                case 1:
                    if (!(page_now instanceof book)) {
                        page_now = new book();
                        page_now.setFlag(0);
                        request.getSession().setAttribute("page", page_now);
                    }
                    break;
                case 3:
                    if (!(page_now instanceof RD)) {
                        page_now = new RD();
                        page_now.setFlag(0);
                        request.getSession().setAttribute("page", page_now);
                    }
                    break;
                case 2:
                    if (!(page_now instanceof user)) {
                        System.out.println("turn to user");
                        page_now = new user();
                        page_now.setFlag(0);
                        request.getSession().setAttribute("page", page_now);
                    }
                    break;
                case 4:
                    System.out.println("ok1");
                    if (!(page_now instanceof RB)) {
                        System.out.println("ok2");
                        page_now = new RB();
                        page_now.setFlag(0);
                        request.getSession().setAttribute("page", page_now);
                    }
                    break;
                case 5:
                    if (!(page_now instanceof RB_)) {
                        page_now = new RB_();
                        page_now.setFlag(0);
                        request.getSession().setAttribute("page", page_now);
                    }
                    break;
            }

        }
        if(page_now==null)
        System.out.println("ok5");
        return page_now;
    }
}
