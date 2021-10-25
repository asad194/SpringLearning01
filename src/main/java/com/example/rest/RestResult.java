package com.example.rest;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResult {
    
    /** リターンコード
     *
     *  正常終了したか、バリデーションに引っかかったかどうかのコード値が入る。
     * */
    private int result;
    
    /** エラーマップ
     * key: フィールド名
     * value: エラーメッセージ
     * バリデーション結果がNGとなったフィールド名と、エラーメッセージを入れる。
     */
    private Map<String, String> errors;
    
}
