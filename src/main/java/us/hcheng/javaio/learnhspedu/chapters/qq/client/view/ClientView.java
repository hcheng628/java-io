package us.hcheng.javaio.learnhspedu.chapters.qq.client.view;

import us.hcheng.javaio.learnhspedu.chapters.qq.client.service.ClientMsgService;
import us.hcheng.javaio.learnhspedu.chapters.qq.client.service.ClientServerMgtService;
import us.hcheng.javaio.learnhspedu.chapters.qq.client.service.SocketService;
import us.hcheng.javaio.learnhspedu.chapters.qq.client.service.UserClientService;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.User;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.util.Utility;

public class ClientView {
	private boolean exit;
	private UserClientService userService;

	public ClientView() {
		userService = new UserClientService();
	}

	public void mainMenu() {
		String choice = null;
		while (!exit) {
			printLevel1Menu();
			choice = Utility.readString(1);
			switch (choice) {
				case "1":
					SocketService.init();
					System.out.print("请输入用户号: ");
					String username = Utility.readString(50);
					System.out.print("请输入密  码: ");
					String pwd = Utility.readString(50);
					User user = new User(username, pwd);

					if (userService.login(user)) {
						System.out.println("===========欢迎 (用户 " + username + " 登录成功) ===========");
						new Thread(new ClientServerMgtService(user), String.join(":", "Client", username)).start();
						while (!exit) {
							printLevel2Menu(username);
							choice = Utility.readString(1);
							switch (choice) {
								case "1":
									ClientMsgService.showOnlineFriends();
									break;
								case "2":
									System.out.println("请输入想对大家说的话: ");
									ClientMsgService.sendMessageToAll(Utility.readString(100), username);
									break;
								case "3":
									System.out.print("请输入想聊天的用户号(在线): ");
									String to = Utility.readString(50);
									System.out.print("请输入想说的话: ");
									ClientMsgService.sendMessageToOne(Utility.readString(100), username, to);
									break;
								case "4":
									System.out.print("请输入你想把文件发送给的用户(在线用户): ");
									to = Utility.readString(50);
									System.out.print("请输入发送文件的路径(形式 d:\\xx.jpg)");
									ClientMsgService.sendFileToOne(Utility.readString(100), username, to);
									break;
								case "9":
									userService.logout(user);
									SocketService.close();
									exit = true;
									break;
							}
						}
					} else
						System.out.println("=========登录失败=========");
					break;
				case "9":
					exit = false;
					break;
			}
		}
	}

	private void printLevel1Menu() {
		System.out.println("===========欢迎登录网络通信系统===========");
		System.out.println("\t\t 1 登录系统");
		System.out.println("\t\t 9 退出系统");
		System.out.print("请输入你的选择: ");
	}

	private void printLevel2Menu(String username) {
		System.out.println("\n=========网络通信系统二级菜单(用户 " + username + " )=======");
		System.out.println("\t\t 1 显示在线用户列表");
		System.out.println("\t\t 2 群发消息");
		System.out.println("\t\t 3 私聊消息");
		System.out.println("\t\t 4 发送文件");
		System.out.println("\t\t 9 退出系统");
		System.out.print("请输入你的选择: ");
	}

}
