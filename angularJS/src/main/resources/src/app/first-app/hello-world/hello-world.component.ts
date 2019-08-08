import { Component, OnInit } from '@angular/core';
import {HelloWorldService} from './hello-world.service';

@Component({
  selector: 'app-hello-world',
  templateUrl: './hello-world.component.html',
  styleUrls: ['./hello-world.component.css']
})
export class HelloWorldComponent implements OnInit {

  public hd = '';

  constructor(public hwS: HelloWorldService) { }

  ngOnInit() {
    this.hwS.init();
  }

  public append(): void {
    if (this.hd.trim() === '') { return; }

    this.hwS.helloWorlds.push(this.hd);
    this.hd = '';
  }

}
