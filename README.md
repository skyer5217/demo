# OVEN SPRINGBOOT DEOM
___
## 安装步骤
#### 1. 下载代码
git clone https://github.com/503612012/demo.git
#### 2. 初始化数据
mysql加载demo.sql文件
#### 3. 编译代码
进入项目根目录执行：mvn clean compile package
#### 4. 启动工程
进入项目根目录执行：./demo.sh start
#### 5. 查看工程当前状态
进入项目根目录执行：./demo.sh status
#### 6. 查看日志
进入项目根目录执行：./demo.sh log
#### 7. 停止工程
进入项目根目录执行：./demo.sh stop
___

## TODO LIST
- [x] 框架搭建
- [x] 登录功能
- [x] shiro权限控制
- [x] 安全拦截
- [ ] 登录验证码
- [ ] 记住我功能
- [x] 403页面还没有编写
- [x] 根据登录用户动态生成菜单
- [x] 菜单点击不能展开
- [x] 菜单的图标
- [x] 菜单点击后的样式
- [x] 角色管理页面
- [x] 用户管理页面
- [x] 菜单管理页面
- [x] 员工管理页面
- [x] 授权页面
- [x] 给用户分配角色页面
- [ ] 菜单栏缩回的按钮点击不能用
- [x] 用户管理模块
- [x] 缓存移除策略
- [x] 登录页面敲回车可以登录
- [x] 用户修改完后页面不跳转
- [x] 删除用户后弹出的确认提示不会关闭
- [x] 用户管理页面，没有权限的按钮不显示，列表页面禁止点击锁定按钮
- [x] 验证各个权限按钮是否起作用
- [ ] 密码错误超过5次的处理
- [ ] 分辨率缩小的话，表格的按钮不起作用了
- [ ] 添加用户的时候，验证用户名是否存在
- [x] 删除角色的时候，判断有没有用户引用这个角色
- [x] 日志管理模块
- [x] 登录信息过期或账号被顶下去后，跳转到登录页面时，跳出iframe框架，同步请求跳转页面，异步请求只提示
- [x] 增加全局异常捕获，所有的controller接口中不用捕获异常，也不用记录请求参数，catch到异常后直接抛出去，全局异常捕获或处理异常并记录请求参数
- [x] 修改源码，更改eleTree.js的位置（改用easyui的tree）
- [x] 修改源码eleTree.js的源码，可以选着一个父节点而不选中它的任何一个子节点（改用easyui的tree）
- [x] 修改源码eleTree.js的源码，节点上是横杠的，也归于选择状态（改用easyui的tree）
- [ ] 引入easyui的代码太多，精简一下
- [ ] 当显示器太小的时候，表格的操作按钮会缩回去，这时候不能点击操作按钮
- [ ] 手机自适应
- [ ] 启动后加载数据字典