# 编译原理第二次实验报告
##### 计24 2012011335 柯均洁

### 0、加入PA1的工作
* 在PA2的框架中加入了PA1的Lexer.l, Parser.y
* 将Tree.java的改动移动到PA2的框架之中

### 1、增加 repeat-until
* BuildSym.java中：增加了visitRepeatLoop
* TypeCheck.java：增加了visitRepeatLoop

### 2、增加 ++ 和 --
* TypeCheck.java：在visitUnary中增加了对PREINC、POSTINC、PREDEC、POSTDEC的处理
	* 判断操作数是不是左值
	* 判断操作数的类型是不是INT
* 增加了lvalueRequiredError来处理++、--操作数不是左值的错误
* 为了支持 **for(...;...;i++)** 的语法，将Parser.y中的SELFINC、SELFDEC变为SimpleStmt

### 3、增加switch-case
* BuildSym.java和TypeCheck.java中增加了visitSwitch, visitSwitchBlock, visitCase, visitDefault, visitCaseBlock
* 增加了IncompatSwitchError来处理switch条件不是int的情况
* 增加了IncompatCaseError来处理case条件不是const int的情况

### 4、增加？：
* 在TypeCheck.java中增加了visitTernary
* 增加了IncompatTestOpError来处理operand类型不一致的错误
