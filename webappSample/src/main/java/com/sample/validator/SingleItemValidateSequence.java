package com.sample.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

import com.sample.validator.Groups.First;
import com.sample.validator.Groups.Second;
import com.sample.validator.Groups.Third;

// バリデーショングループに @GroupSequence で順番を指定する。
// 指定した順にチェックされる。同じグループのチェックが全てOKとならない限りは、次のチェックに移らない。
@GroupSequence({Default.class, First.class, Second.class, Third.class})
public interface SingleItemValidateSequence {

}
