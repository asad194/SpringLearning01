package com.example.controller;

import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.application.service.UserApplicationService;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;

import lombok.extern.slf4j.Slf4j;
/***
 * ユーザー登録を押された時に、どの処理呼んで画面に何を出すか伝えるのが役割。橋渡し役。ka
 * @author asad
 *
 */
@Controller
/*ユーザーが
http://localhost:8080/user
をリクエストした時に、
このコントローラを実行するようにマッピングしている
*/
@RequestMapping("/user")
//Simple Logging Facade For Java。色々なロギングシステムからどれを使えばいいか？　という選択を肩代わりしてくれる。
@Slf4j
public class SignupController {

	@Autowired
	private UserApplicationService userApplicaitonService;

	@Autowired
	private UserService userService;

	// JavaConfigでBean登録したからAutowired出来る。
	@Autowired
	private ModelMapper modelMapper;

	/** ユーザー登録画面を表示 */
	/*/signupとGETメソッドのHTTPリクエストが来たら、
このメソッドを実行する。そしてこのメソッドは取得処理をするもの、と示している。*/
	@GetMapping("/signup")
	/* ModelAttributeについて
	 * 付与したクラスのインスタンスを、
	 	自動でModelに登録する。

		以下のコードを自動で書いてくれるイメージ
		model.addAttribute("signupForm", form);

		クラス名の先頭を小文字にした文字列がModelのキーに登録される。

		！！！！
		※Modelにインスタンスを登録することで、画面にインスタンスを渡せる
	*/
	//Model クラス を 使う こと で、 別 の 画面 に 値 を 渡す こと が でき ます。 Model クラス の addAttribute メソッド に キー 名 と 値 を 指定 し ます。
	public String getSignup(Model model, Locale locale, @ModelAttribute SignupForm form) {
		//性別を取得
		Map<String, Integer> genderMap = userApplicaitonService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);
		// ユーザー登録画面に遷移
		return "user/signup";
	}

	/** ユーザー登録処理 */
	/**
	 * 8/15読解内容
	 * ユーザーが登録画面で入力した内容がformに入っていて
		MUserとフィールド名が同一なのでmapメソッドで変換している。

		その後、UserService#signupがmapper#insertOneを呼んでおり、
		UserMapper.xmlでmapperタグによりinsertOneがマッピングされているので、
		UserMapper.xmlのinsertOneのSQlが実行されて、
		ユーザーが入力したformがDBへ登録される。
	 * @param model
	 * @param locale
	 * @param form
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/signup")
	public String postSignUp(Model model, Locale locale, @ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult) {
		//入力チェック結果
		if(bindingResult.hasErrors()) {
			//NG：ユーザー登録画面に戻ります
			return getSignup(model, locale, form);
		}
		log.info(form.toString());

		// formをMUserクラスに変換
		// ModelMapperのmapメソッドを使えばフィールドの内容を簡単にコピーできる。
		MUser user = modelMapper.map(form, MUser.class);

		// ユーザー登録
		userService.signup(user);

		// ログイン画面にリダイレクト
		return "redirect:/login";
	}

}
