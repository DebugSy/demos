package com.shiy.spring.restapi;

import com.datapps.grassland.server.entity.Schema;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.inheritance;
import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.title;

/**
 * Created by DebugSy on 2017/8/16.
 */
public class SpringRestTemplate {

	public static void main(String[] args) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("name", "admin");
		map.add("password", "123456");
		map.add("version", "carpo-2.0-b84-2017-06-16");
		//如果带了header，可以加入HttpEntity里面
//		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = rest.postForEntity("http://node3:7510/api/auth/login", map, String.class);
		HttpStatus statusCode = response.getStatusCode();
		if (statusCode.is2xxSuccessful()){
			System.out.println("登陆成功！");

			HttpHeaders headers = new HttpHeaders();
			String token = response.getHeaders().getFirst("X-AUTH-TOKEN");
			headers.add("X-AUTH-TOKEN", token);
			System.out.println(token);
			headers.setContentType(MediaType.APPLICATION_JSON);

			/**
			 * 没有携带header的get请求
			 */
//			ResponseEntity<Schema> entity = rest.getForEntity("http://node3:7510/api/schemas/name/khhz", Schema.class, headers);
			HttpEntity httpEntity = new HttpEntity(headers);
			ResponseEntity<Schema> entity = rest.exchange("http://node3:7510/api/schemas/name/khhz", HttpMethod.GET, httpEntity, Schema.class);

			Schema schema = entity.getBody();
			System.out.println(schema);

		} else {
			System.out.println("登陆失败！");
		}





	}

}
