
MVVM 架构理解
ViewModel 作为 Model 和 View 的桥梁，只做通知操作
具体的 UI 变化，数据存储 分别是在 View 层（Adapter 属于 View） Model 层（Dao 属于 Model 层） 进行对应的操作

- 单向数据流 ：用户操作 → View → ViewModel → Model → LiveData → View
- 线程安全 ：数据操作在 IO 线程执行，UI 更新在主线程执行
- 数据驱动 UI ：UI 完全由 LiveData 的数据状态驱动，确保一致性
- 内存存储 ：所有数据存储在内存中，应用重启后数据会丢失


- LiveData ：实现数据与 UI 的响应式绑定
- ViewModel ：分离业务逻辑，避免内存泄漏
- RecyclerView ：高效展示列表数据
- 协程 ：处理异步操作，保证 UI 流畅