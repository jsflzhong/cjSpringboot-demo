import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HelloWorldService {

  // hello world 列表, 模拟数据源.
  public helloWorlds: string[] = [];

  // http url (用本地json文件模拟)
  readonly url = 'assets/datasource.json';

  // 构造器, 载入http包对象
  constructor(private httpClient: HttpClient) { }

  /**
   * 初始化字段数据
   * 注意,该init方法,是在component那边调用的.
   */
  public init(): void {
    // 初始化数据源
    // this.helloWorlds = ["你好世界", "مرحبا بالعالم", "Salut tout le monde"];

    // 打印在f12中的控制台中.
    console.log(`@@@http start getting the data`);

    // 用HTTP获取数据
    this.httpClient.get(this.url).subscribe((data: { helloWorlds: string[] }) => {
      this.helloWorlds = data.helloWorlds;
      console.log(`@@@http got the data:  ${data.helloWorlds}`);
    }, (error) => {
      // 失败回调
      console.log('@@@error when http request!');
    });
  }
}
