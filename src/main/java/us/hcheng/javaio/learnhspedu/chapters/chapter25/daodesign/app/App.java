package us.hcheng.javaio.learnhspedu.chapters.chapter25.daodesign.app;

import us.hcheng.javaio.learnhspedu.chapters.chapter25.daodesign.domain.HspUser;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.daodesign.service.HspUserService;

public class App {

    public static void main(String[] args) {
        int id = 1;
        HspUserService userService = new HspUserService();
        userService.createHspUser(new HspUser(id, "Moka", "MokaPass"));
        System.err.println(userService.queryHspUserById(id));
        System.err.println("name: " + userService.getHspUserNameById(id));
        userService.deleteHspUser(1);
        System.err.println(userService.queryHspUserById(id));
        System.err.println(userService.getWeakPwdUsersOne());
        System.err.println(userService.getWeakPwdUsers());
    }

}
