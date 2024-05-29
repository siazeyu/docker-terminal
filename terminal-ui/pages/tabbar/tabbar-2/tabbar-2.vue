<template>
  <div>
    <uni-table border emptyText="暂无更多数据" stripe>
      <!-- 表头行 -->
      <uni-tr>
        <uni-th width="100px">名称</uni-th>
        <uni-th width="100px">镜像</uni-th>
        <uni-th width="100px">状态</uni-th>
        <!-- <uni-th width="100px">操作</uni-th> -->
      </uni-tr>
      <!-- 表格数据行 -->
      <uni-tr v-for="(item,index) in contianers">
        <uni-td>{{ item.Names[0] }}</uni-td>
        <uni-td>{{ item.Image }}</uni-td>
        <uni-td>{{ item.Status }}</uni-td>
        <uni-td class="mini-btn">
          <button type="primary" @click="">停止</button>
        </uni-td>
        <uni-td class="mini-btn">
          <button type="primary" @click="">运行</button>
        </uni-td>
        <uni-td class="mini-btn">
          <button type="warn" @click="">删除</button>
        </uni-td>
        <uni-td class="mini-btn">
          <button type="primary" @click="goto(item.Id)">进入控制台</button>
        </uni-td>
      </uni-tr>

    </uni-table>

  </div>
</template>

<script>
import {getContainers} from '@/utils/api.js'

export default {
  data() {
    return {
      contianers: []
    }
  },
  onLoad() {
    this.init()
  },
  methods: {
    init() {
      getContainers().then(res => {
        console.log(res.data)
        this.contianers = res.data
      })
    },
    goto(id) {
      uni.navigateTo({url: '/pages/tabbar/tabbar-1/tabbar-1?containerId=' + id})
    }
  }
}
</script>

<style>
.content {
  text-align: center;
  height: 400upx;
  margin-top: 200upx;
}
</style>
