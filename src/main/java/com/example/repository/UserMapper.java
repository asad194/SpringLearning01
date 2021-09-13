package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.user.model.MUser;

@Mapper
public interface UserMapper {
	/** ユーザー登録 */
	public int insertOne(MUser user);

	/** ユーザー取得。
	 * 複数件のユーザー情報を取得する
	 * select文の実行結果が複数件になるので、
	 * メソッドの戻り値をListにしている。
	 *
	 *  UserMapper.xmlのselectタグでfindManyを指定しているので、このメソッドを呼び出すとUserMapper.xmlの該当SQLが実行される。*/
	public List<MUser> findMany();

	/** ユーザー取得(1件) */
	public MUser findOne(String userId);

}
