import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'v1-edit-request-panel',
  templateUrl: './v1-edit-request-panel.component.html',
  styleUrls: ['./v1-edit-request-panel.component.scss']
})
export class V1EditRequestPanelComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
public saveRequest(){}
public isFormValid( form:boolean):boolean {
    return true
}
}
