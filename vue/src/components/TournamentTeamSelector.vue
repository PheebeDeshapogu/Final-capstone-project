<template>
    <div class="flex-item">
        <label for="teamSelect"></label>
        <div class="dropDown" v-if="roundNum === 0 && this.$store.state.bracketData[storeIndex].showInput && numOfTeams != 1">
            <select id="teamSelect" v-model="selectedTeam" @change="onTeamChange()">
                <option value="">Select a team</option>
                <option v-for="team in teams" :key="team.id" :value="team.teamName">{{ team.teamName }} </option>
            </select>
        </div>
       
        
        <div class="teamScore">
             <div class="teamSelect">
                {{this.$store.state.bracketData[storeIndex].teamName}}
            </div>
            <div class="scoreItems" v-if="this.$store.state.bracketData[storeIndex].showInput && 
            numOfTeams != 1">
                <label for="score" class="scoreLabel">Score: </label>
                <input type="number" id="score" class="scoreInput" v-model="score" @input="onScoreChange" >
            </div>
        </div>
        <div>
        <span class="scoreDisplay"
        v-if="!this.$store.state.bracketData[storeIndex].showInput"> Score: {{this.$store.state.bracketData[storeIndex].score}} </span>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    data() {
        return {
            score: 0,
            selectedTeam: "",
            storeIndex: 0,
        }
    },
    props: {
        teams: Array,
        numOfTeams: Number,
        numTeams: Number,
        roundNum: Number,
    },
    methods: {
        ...mapActions(['updateTeamScore', 'updateTeamName']),

        onScoreChange(){
            let team = {storeIndex: "", score: ""};
            team.storeIndex = this.storeIndex;
            team.score = this.score;
            this.$store.commit("SET_TEAM_SCORE", team);
        },

        onTeamChange() {
            // const selectedTeamData = this.teams.find(team => team.id === this.selectedTeam);
            // if (selectedTeamData) {
            //     // Update the team name in the store
            //     this.updateTeamName({ teamId: this.selectedTeam, teamName: selectedTeamData.teamName });
            // }
            // this.score = 0; // Reset score when team changes
            let team = {storeIndex: "", selectedTeam: ""};
            team.storeIndex = this.storeIndex;
            team.selectedTeam = this.selectedTeam;
            this.$store.commit("SET_TEAM_NAME", team)
        },
    },
    computed: {
        teamBracketData() {
            return this.$store.state.bracketData[this.storeIndex];
        },
    },
    created() {
        this.storeIndex = this.$store.getters.getTeamIndex;
        this.$store.commit("SET_INDEX", this.storeIndex);
        this.$store.commit("ADD_1_TO_TEAM_INDEX");
        this.selectedTeam = this.$store.state.bracketData[this.storeIndex].teamName;
        this.score = this.$store.state.bracketData[this.storeIndex].score
    },
}
</script>

<style>
#teamSelect {
    width: 100%;
    flex-wrap: wrap;
    font-size: large;
    text-wrap: wrap;
}

.scoreItems {
    display: flex;
    align-items: center;
    font-size: large;
}

.scoreInput{
    min-width: 50%;
}

.scoreDisplay{ 
    font-size: large;
}

</style>