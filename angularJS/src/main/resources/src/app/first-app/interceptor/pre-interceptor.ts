import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';

/**
 * http拦截器.
 * 需要注入到根模块app.module.ts中的头部和providers模块中.
 */
@Injectable()
export class PreInterceptor implements HttpInterceptor{

  /**
   * 业务逻辑执行
   *
   * 如果要给所有请求加一个认证头部，可以操作其中的req参数，注意req参数为只读的，必须执行其clone方法得到副本来操作，处理完的副本通过next参数发射出去即可
   *
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('@@@PreInterceptor is running...')
    return next.handle(req);
  }

}
