package miran.blog.netty;

//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NioServer {
    private static int port = 9999;
    public static void main(String[] args) {
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            // 服务端的BootStrap，一个需要接受连接，一个用来处理事件
//            b.group(bossGroup, workerGroup)
//                    // Channel理解为通道，网络传输的通道类型
//                    .channel(NioServerSocketChannel.class)
//                    // ChannerlHandler要被装进ChannelPipeline
//                    // 而过程则是调用ChannelInitializer的iniChannel方法
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        public void initChannel(SocketChannel ch) throws Exception {
//                            // addLast，在PipeLine中的Handler是有顺序的，所以
//                            // 有addLast，addFirst等方法
//                            // 添加的同时，可以指定Encoder和Decoder
////                            ch.pipeline().addLast(new RequestDecoder(),
////                                    new ResponseDataEncoder(),
////                                    new ProcessingHandler());
//                        }
//                    }).option(ChannelOption.SO_BACKLOG, 128)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true);
//
//            ChannelFuture f = b.bind(port).sync();
//            f.channel().closeFuture().sync();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//        }
    }
}
