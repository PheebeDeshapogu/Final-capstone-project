<template>
    <h2 class="matchTitle"></h2>
    <TeamSelector v-if="!isFinalRound" v-bind:roundNum="roundNum" v-bind:teams="teams" v-for="number in numOfTeams"
        :key="number" v-bind:showScoreBtn="showSaveScoreBtn" v-bind:numOfTeams="numOfTeams"></TeamSelector>
    <button v-if="this.$store.state.bracketData[matchIndex].showInput && numOfTeams==2"
     v-on:click="saveScores" class="saveBtn">Save Scores</button>
    <TeamSelector v-if="isFinalRound" v-bind:roundNum="roundNum" ></TeamSelector>
</template>

<script>
import BracketService from '../services/BracketService';
import TeamSelector from './TournamentTeamSelector.vue';

export default {
    data() {
        return {
            matchIndex: Number,
            clickedSaveScore: false,
            showSaveScoreBtn: true,
        }
    },
    props: {
        numTeams: Number,
        teams: Array,
        numOfTeams: Number,
        isFinalRound: Boolean,
        matchNumber: Number,
        roundNum: Number,
    },
    methods: {
        saveScores() {
            this.clickedSaveScore = true;
            let brackets = this.$store.getters.getBracketData;
            let scoresDto = {
                team1Name: '',
                team1Score: '',
                team2Name: '',
                team2Score: '',
            }
            let teams1And2 = [];
            brackets.forEach((item) => {
                if (item.round == this.roundNum + 1 && item.seat == this.matchNumber) {
                    teams1And2.push(item);
                    console.log(item);
                }
            })
            scoresDto.team1Name = teams1And2[0].teamName;
            scoresDto.team1Score = teams1And2[0].score;
            scoresDto.team2Name = teams1And2[1].teamName;
            scoresDto.team2Score = teams1And2[1].score;
            console.log(scoresDto);
            BracketService.saveScore(this.$route.params.id, scoresDto);  
            let team = {
                storeIndex: '',
                showInput: false,
            };
            team.storeIndex = this.matchIndex;
            this.$store.commit("SET_SHOW_INPUT", team);
            team.storeIndex =this.matchIndex+1;
            this.$store.commit("SET_SHOW_INPUT", team);
        }
    },
    created() {
        this.matchIndex = this.$store.getters.getTeamIndex;
        let team = {
            storeIndex: '',
            round: '',
            seat: '',
        };
        team.round = this.roundNum + 1;
        team.storeIndex = this.matchIndex;
        team.seat = this.matchNumber;
        this.$store.commit("SET_MATCH_ROUND", team);
        this.$store.commit("SET_SEAT", team);
        if (this.numOfTeams == 2) {
            team.storeIndex = this.matchIndex + 1;
            this.$store.commit("SET_MATCH_ROUND", team);
            this.$store.commit("SET_SEAT", team);
        }
    },
    computed: {
    },

    components: { TeamSelector }
}
</script>

<style>
.saveBtn {
    background-color: purple;
    color: white;
    width: 120px;
    height: 35px;
    padding: 10px 10px;
    border-radius: 8px;
    cursor: pointer;
    box-shadow: 2px 2px 5px blue;
    font-size: 16px;
    white-space: nowrap;
}
</style>