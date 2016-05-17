import {Component,Output,EventEmitter} from 'angular2/core';
import {PlantCatalogService} from '../catalog.service';
import {Plant} from '../declarations';

@Component({
  selector: 'selection-component',
  templateUrl: '/app/phr/selection/selection.html'
})
export class SelectionComponent {
  @Output() selectPlantEvent : EventEmitter<Plant> = new EventEmitter();
  constructor(public catalog: PlantCatalogService) {}
  selectPlant(plant: Plant) {
    this.selectPlantEvent.emit(plant);
  }
}
