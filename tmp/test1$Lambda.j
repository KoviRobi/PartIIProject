.class test1$Lambda
.super rmk35/partIIProject/backend/runtimeValues/LambdaValue



.method public <init>()V
  .limit stack 1
  .limit locals 1
  aload_0
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/<init>()V
  return
.end method

.method public static <clinit>()V
  .limit stack 0
  .limit locals 0

  return
.end method

.method public run(Ljava/util/List;)Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
  .limit stack 8
  .limit locals 2
  aload_1
  dup
  iconst_0
  invokeinterface java/util/List/get(I)Ljava/lang/Object; 2
  astore_1
  pop
  ; JavaCallStatement
  ; JavaMethodStatement
  ; JavaFieldStatement
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "java.lang.System"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "out"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  invokestatic rmk35/partIIProject/backend/runtimeValues/IntrospectionHelper/getStaticField(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "println"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  iconst_1
  anewarray java/lang/String
  dup
  iconst_0
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "java.lang.Object"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  aastore
  invokestatic rmk35/partIIProject/backend/runtimeValues/IntrospectionHelper/getMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/reflect/Method;
  checkcast java/lang/reflect/Method
  ; JavaFieldStatement
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "java.lang.System"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "out"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  invokestatic rmk35/partIIProject/backend/runtimeValues/IntrospectionHelper/getStaticField(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
  iconst_1
  anewarray java/lang/Object
  dup
  iconst_0
  ; LocalIdentifier Get
  aload_1
  aastore
  invokevirtual java/lang/reflect/Method/invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
  areturn
.end method

