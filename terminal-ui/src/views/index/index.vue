<template>

  <h1>Terminal</h1>

</template>
<script>
export default {
  name: 'Terminal',
  data: () => {
    return {

    }
  },
  mounted: function () {

  },
  created: function(){
    this.initWebSocket();
  },
  destroyed: function () { // 离开页面生命周期函数
    this.websocketclose();
  },
  methods: {
    initWebSocket(){ //初始化weosocket
      const wsuri = "ws://127.0.0.1:8090/websocket/terminal";
      this.websock = new WebSocket(wsuri);
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen(){ //连接建立之后执行send方法发送数据
      console.log("连接成功！");
    },
    websocketonerror(e){//连接建立失败重连
      console.log("连接失败！", e);
      // this.initWebSocket();
    },
    websocketonmessage(r){ //数据接收
      console.log("接收数据：" + r.data)
    },
    websocketsend(Data){//数据发送
      this.websock.send(Data);
    },
    websocketclose(e){  //关闭
      console.log('断开连接',e);
    }
}
}
</script>