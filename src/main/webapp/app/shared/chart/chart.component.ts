import { Component, Input, ViewChild, OnChanges } from '@angular/core';

import { BaseChartDirective } from 'ng2-charts/ng2-charts'

@Component({
  selector: 'chart-component',
  templateUrl: './chart.component.html'
})
export class ChartComponent implements OnChanges {

  @Input() public chartLabels: string[];
  @Input() public chartData: number[];
  @Input() public chartType: string;

  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  ngOnChanges() {
    if (this.chart.chart != null) {
      this.chart.chart.config.data.labels = this.chartLabels;
    }
  }

  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }
}
