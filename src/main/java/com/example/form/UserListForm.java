package com.example.form;

import lombok.Data;

/**
 * ユーザー一覧画面のフォーム用。
 * 画面から入力されたユーザー検索条件を持つためのクラス。
 * @author asad
 *
 */
@Data
public class UserListForm {
	private String userId;
	private String userName;
}
