; Question: does JVM empty stack after call return?
; Verdict: yes, otherwise it would try to call 1.println(3)
.class poptest
.super java/lang/Object

.method public <init>()V
  aload_0
  invokenonvirtual java/lang/Object/<init>()V
  return
.end method

.method static test(II)I
  .limit stack 2
  .limit locals 2
  .line 1
  iload_0
  iload_1
  ireturn
.end method

.method public static main([Ljava/lang/String;)V
  .limit stack 3
  .line 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  .line 3
  iconst_1
  iconst_3
  invokestatic poptest/test(II)I
  .line 4
  invokevirtual java/io/PrintStream/println(I)V
  .line 5
  iconst_1
  iconst_2
  invokestatic poptest/test(II)I
  .line 6
  return
.end   method