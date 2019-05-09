import { Component, ViewChild } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { Skill } from '../../models/Skill';
import { Chart } from 'chart.js';

/**
 * Generated class for the ChartPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage({
  name: "chart"
})
@Component({
  selector: 'page-chart',
  templateUrl: 'chart.html',
})
export class ChartPage {
  @ViewChild('barCanvas') barCanvas;
  barChart: any;
  skills: string[] = [];
  relevances: number[] = [];

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    var skillObjects: Skill[] = navParams.get("skills")
    for (let i = 0; i < skillObjects.length; i++) {
        this.skills.push(skillObjects[i].name)
        this.relevances.push(skillObjects[i].relevance)
    }
  }

  ionViewDidLoad() {
    let me = this;
    this.barChart = new Chart(this.barCanvas.nativeElement, {

      type: 'bar',
      data: {
        labels: me.skills,
        datasets: [{
          label: 'Relevance',
          data: me.relevances,
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)',
            'rgb(122, 206, 26, 0.2)',
            'rgb(224, 0, 0, 0.2)',
            'rgb(229, 226, 29, 0.2)',
            'rgb(29, 68, 175, 0.2)',
            'rgb(216, 110, 17, 0.2)'
          ],
          borderColor: [
            'rgba(255,99,132,1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)',
            'rgb(122, 206, 26, 1)',
            'rgb(224, 0, 0, 1)',
            'rgb(229, 226, 29, 1)',
            'rgb(29, 68, 175, 1)',
            'rgb(216, 110, 17, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: true,
              autoSkip: false,
              stepSize: 10,
              max: 100,
              // Include a percentage sign in the ticks
              callback: function(value, index, values) {
                return value + '%';
            }
            }
          }],
          xAxes: [{
            ticks: {
              beginAtZero: true,
              autoSkip: false
            }
          }]
        }
      }

    });
  }

}
