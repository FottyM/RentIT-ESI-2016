import {Component, Output, EventEmitter} from 'angular2/core';

import {Query} from '../declarations';

@Component({
    selector: 'query-component',
  templateUrl: '/app/phr/query/query.html'
})
export class QueryComponent {
    @Output() executeQueryEvent: EventEmitter<Query> = new EventEmitter();
    
    query: Query = new Query();
    executeQuery() {
      
        this.executeQueryEvent.emit(this.query);
    }
}
