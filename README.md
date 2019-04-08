# JPA

## 基本注解

+ @entity 表示是一个实体类

+ @Table 指定表

+ @Id 指定主键

+ @GeneratedValue 指定主键生成策略

  + IDENTITY：采用数据库 ID自增长的方式来自增主键字段，Oracle 不支持这种方式；
  + **AUTO**： **JPA自动选择合适的策略，是默认选项；**
  + SEQUENCE：通过序列产生主键，通过 @SequenceGenerator 注解指定序列名，MySql 不支持这种方式
  + TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植

+ @column 指定列名

+ @Basic ：用于没有任何标注的 getXxx() 方法，默认即为 @Basic,所以若一个 getter 方法无任何注解，可以使用 @Basic 注解，也可以不使用 

+ @Transient 表示不需要映射

+ @Temporal 调整精度 比如Date的精度

+ @OneToOne 一对一映射

  + (cascade = CascadeType.ALL) 级联操作

+ @JoinColumn

  + (name="" 表示外键名字 referencedColumnName=“” 表示对应的列)

+ @OneToMany 一对多

  + （fetch=FetchType.LAZY 加载方式 默认为懒加载 
  +  mappedBy=" " 拥有mappedBy注解的实体类为关系被维护端）

+ @ManyToOne

  + (可选属性optional=false,表示该属性不能为空。删除该属性，不影响主属性)

+ @ManyToMany

  + (mappedBy=" " 拥有mappedBy注解的实体类为关系被维护端）

+ @JoinTable

+     @JoinTable(name = "user_authority",joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
      //1、关系维护端，负责多对多关系的绑定和解除
      //2、@JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(User)
      //3、inverseJoinColumns指定外键的名字，要关联的关系被维护端(Authority)
      //4、其实可以不使用@JoinTable注解，默认生成的关联表名称为主表表名+下划线+从表表名，
      //即表名为user_authority
      //关联到主表的外键名：主表名+下划线+主表中的主键列名,即user_id
      //关联到从表的外键名：主表中用于关联的属性名+下划线+从表的主键列名,即authority_id
      //主表就是关系维护端对应的表，从表就是关系被维护端对应的表
 
## JPQL
**注意：JPQL语句操作的是实体**
### 三种方法

+ createQuery 用于执行jpql语句

+ create NamedQuery  用于执行实体类前@NamedQuery标记的语句

+ createNativeQuery 用于执行sql语句

### 接收方法

+ List getResultList()   用于执行select语句并返回结果集实体列表。

+ Object getSingleResult()  用于执行只返回单个结果实体的select语句
### 两种语句
+ select o from Orders o where o.id = :myId   setParameter中第一个参数为：后的名字

+ select o from Order o where o.id = ?1   setParameter中第一个参数为？后的索引值


### 设置参数

+ setParameter() 不同场景的参数内容不同

  

### 查询所有的语句

select o from Order o 
