package com.investdata.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.investdata.common.BaseAction;

/**
 * 生成图片验证码Action
 */
public class AuthImageAction extends BaseAction implements SessionAware, ServletResponseAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private HttpServletResponse response = null;
	private Map<String, Object> session = null;
	private static Font imageFont = new Font("Times New Roman", Font.PLAIN, 17);
	Logger _log = Logger.getLogger(AuthImageAction.class);
	
	/**
	 * 生成图片验证码
	 * 注意：返回Json数据或者类似生成图片验证码，必须return null 
	 * 否则功能虽正常，但后台会报错！
	 */
	public String execute() throws Exception {
		_log.info("进入生成图片验证码流程");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 表明生成的响应是图片
		response.setContentType("image/jpeg");

		int width = 70, height = 42;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(1, 1, width - 1, height - 1);
		g.setColor(new Color(102, 102, 102));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(imageFont);

		g.setColor(getRandColor(160, 200));

		// 画随机线
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 从另一方向画随机线
		for (int i = 0; i < 70; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(12) + 1;
			int yl = random.nextInt(6) + 1;
			g.drawLine(x, y, x - xl, y - yl);
		}

		//随机生成字母和数字 6位
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			if(i % 2 ==0) {
				int randNum = random.nextInt(9);
				sRand += String.valueOf(randNum);
				g.drawString(String.valueOf(randNum), 15 * i + 10, 24);
			}else {
				int itmp = random.nextInt(26) + 65;
				char ctmp = (char) itmp;
				g.drawString(String.valueOf(ctmp), 15 * i + 10, 24);
				sRand += String.valueOf(ctmp);
			}
		}

		session.put("authImage", sRand);
		_log.info(String.format("生成的图片验证码:[%s]", sRand));
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
		return null;
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

}
