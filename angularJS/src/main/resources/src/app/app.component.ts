/*从Angular核心模块里面引入了component装饰器*/
import { Component } from '@angular/core';

/*用装饰器定义了一个组件 和 组件的元数据.  所有的"组件"都必须使用这个装饰器来注解.*/
@Component({
  /*
  * 组件元数据. Angular会通过这里面的属性来渲染组件并执行逻辑.
  * selector就是CSS选择器,表示这个组件可以通过app-root来调用，index.html中有个<app-root>Loading...</app-root>标签，这个标签用来展示该组件的内容.
  * templateUrl  组件的模板，定义了组件的布局和内容
  * styleUrls   该模板引用那个css样式
  * */
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
/*
AppComponent本来就是一个普通的 typescript 类，
但是上面的组件元数据装饰器告诉Angular，AppComponent是一个组件，需要把一些元数据附加到这个类上，
Angular就会把AppComponent当组件来处理
即: 用@Component注解来指明一个类是一个组件(component).
这个类实际上就是该组件的控制器，我们的业务逻辑就是在这个类中编写.
*/
export class AppComponent {
  title = 'resources';
}
