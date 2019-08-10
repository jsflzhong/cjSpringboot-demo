// Angular浏览器解析模块
import { BrowserModule } from '@angular/platform-browser';
// Augular核心模块. 注意括号里的是具体模块名字.
import { NgModule } from '@angular/core';
// 根组件
import { AppComponent } from './app.component';
import { HelloWorldComponent } from './first-app/hello-world/hello-world.component';

// 引入httpClient. 原生的http组件没有/common/这一层.
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {PreInterceptor} from './first-app/interceptor/pre-interceptor';

/*@NgModule装饰器, 接受一个元数据对象, 告诉Angular如何编译和启动应用*/
@NgModule({
  declarations: [ /*配置当前项目运行的组件*/
    // 声明模块里有什么东西 只能声明：组件/指令/管道
    AppComponent,
    HelloWorldComponent
  ],
  imports: [ /*配置当前项目运行所依赖的其他模块*/
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [{ /*配置当前项目运行所需要的服务*/
    provide: HTTP_INTERCEPTORS,
    useClass: PreInterceptor,
    multi: true,
  }],
  // 声明模块的主组件是什么
  bootstrap: [AppComponent] /*指定应用的主视图(称为根组件), 通过引导根AppModule来启动应用*/
})

/*根木块不需要导出任何东西, 因为其他组件不需要导入根模块.*/
export class AppModule { }
