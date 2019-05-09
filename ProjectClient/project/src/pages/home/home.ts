import { Component } from '@angular/core';
import { NavController, LoadingController } from 'ionic-angular';
import { ApiServiceProvider } from '../../providers/api-service/api-service';
import { Skill } from '../../models/Skill';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  skills: Skill[] = []
  skillId: string;
  loading;
  readonly queryTypes = {
    DATABASE: "db",
    LIVE_CRAWL: "query"
  }

  constructor(public navCtrl: NavController, private apiService: ApiServiceProvider,
    public loadingCtrl: LoadingController) {
    this.getAllSkills()
  }

  getAllSkills() {
    let me = this;
    me.presentLoading('Getting ready. Please wait...');
    me.apiService.getAllSkills().then((result) => {
      console.log("response from getAllSkills:")
      console.log(result)

      // Parse skills json response
      Object.keys(result).forEach((key, index) => {
        var skill: Skill = new Skill()
        skill.id = key
        skill.name = result[key]
        me.skills.push(skill)
      })

      me.skills.sort((a: Skill, b: Skill) => {
        if (a.name.toLocaleLowerCase() < b.name.toLocaleLowerCase()) return -1;
        if (a.name.toLocaleLowerCase() > b.name.toLocaleLowerCase()) return 1;
        return 0;
      })

      // Set first skill selected by default
      if (me.skills.length > 0) {
        me.skillId = me.skills[0].id
      }

      me.dismissLoading()

      console.log("Converted to Skills Array:")
      console.log(me.skills)
    }, (error) => {
      me.dismissLoading()
      console.log("error from getAllSkills:")
      console.log(error)
    })
  }

  findRelevantSkills(queryType: string) {
    let me = this;
    var relevantSkills: Skill[] = [];

    var message;
    if (queryType == me.queryTypes.DATABASE) {
      message = "Finding relevant skills...";
    } else {
      message = "Finding relevant skills using live crawl. This might take a while..."
    }
    me.presentLoading('Finding skills...');
    
    me.apiService.getRelevantSkills(me.skillId, queryType).then((result) => {
      console.log("response from getRelevantSkills:")
      console.log(result)
      // Parse relevant skills json response
      Object.keys(result).forEach((key, index) => {
        var skill: Skill = new Skill();
        skill.name = key;
        skill.relevance = Number(result[key] * 100)
        relevantSkills.push(skill)
      })

      relevantSkills.sort((a: Skill, b: Skill) => {
        if (a.relevance > b.relevance) return -1;
        if (a.relevance < b.relevance) return 1;
        return 0;
      })

      me.dismissLoading()
      // Open chart page
      me.openChartPage(relevantSkills)

      console.log("Converted to Skill objects:")
      console.log(relevantSkills)
    }, (error) => {
      me.dismissLoading()
      console.log("error from getAllSkills:")
      console.log(error)
    })
  }

  presentLoading(message: string) {
    this.loading = this.loadingCtrl.create({
      content: message
    });

    this.loading.present();
  }

  dismissLoading() {
    this.loading.dismiss()
  }

  openChartPage(skills: Skill[]) {
    this.navCtrl.push("chart", { skills: skills })
  }

}
