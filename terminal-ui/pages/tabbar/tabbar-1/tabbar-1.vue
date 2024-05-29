<template>
  <div>
    <div ref="terminal"></div>
  </div>
</template>

<script>
import {
  Terminal
} from "xterm";

import "xterm/css/xterm.css";

export default {
  data() {
    return {
      termConfig: {
        cols: 80,
        rows: 37,
        cursorBlink: true, // 光标闪烁
        cursorStyle: "block", // 光标样式  null | 'block' | 'underline' | 'bar'
        scrollback: 600, //回滚
        tabStopWidth: 8, //制表宽度
        screenKeys: true
      },
      websock: null,
      term: null
    };
  },
  mounted() {
    this.initWebSocket();
    this.openTerminal()
  },
  onLoad: function (option) {
    console.log(option); //打印出上个页面传递的参数。
    this.id = option.containerId
  },
  destroyed() {
    this.websock.close()
  },
  methods: {
    openTerminal() {
      this.term = new Terminal({
        cols: 97,
        rows: 37,
        cursorBlink: true, // 光标闪烁
        cursorStyle: "block", // 光标样式  null | 'block' | 'underline' | 'bar'
        scrollback: 800, //回滚
        tabStopWidth: 8, //制表宽度
        screenKeys: true
      });
      let parent = this
      this.term.onData(function (data) {
        //键盘输入时的回调函数
        parent.websocketsend(data)
      });
      this.term.open(this.$refs['terminal']);
      //在页面上显示连接中...


    },
    initWebSocket() { //初始化weosocket
      const wsuri = "ws://127.0.0.1:8090/websocket/terminal/" + this.id;
      this.websock = new WebSocket(wsuri);
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen() { //连接建立之后执行send方法发送数据

    },
    websocketonerror(error) { //连接建立失败重连
      this.term.write('Error: ' + error + '\r\n');
    },
    websocketonmessage(data) { //数据接收
      console.log(data)
      this.term.write(data.data);
    },
    websocketsend(Data) { //数据发送
      this.websock.send(Data);
    },
    websocketclose(e) { //关闭
      term.write("\rconnection closed");
    }
  }

};
</script>

<style>
.content {
  text-align: center;
  height: 400upx;
  margin-top: 200upx;
}
</style>