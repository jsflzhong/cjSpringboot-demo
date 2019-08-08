import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HelloWorldComponent } from './first-app/hello-world/hello-world.component';

// 引入httpClient. 原生的http组件没有/common/这一层.
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {PreInterceptor} from './first-app/interceptor/pre-interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HelloWorldComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: PreInterceptor,
    multi: true,
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
