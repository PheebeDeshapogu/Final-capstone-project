<template>
    <div class="team-form">

        <form @:submit="submitTeam()">
            <h1 id="team-form-title">Team Sign-Up</h1>

            <p> Register your team to compete!</p>

            <div class="form-filling">
            <label for="team-name">Team name</label>
            <input type="text" id="team-name" name="team-name" v-model="team.teamName" required>
            </div>

            <div>
            <label for="gameInput">Game of choice</label>
            <select id="gameInput" v-model="selectedGameName" @change="handleGameChange">
                <option disabled value="">Please select one</option>
                <option v-for="game in games" :key="game.gameId" :value="game.gameName">{{ game.gameName }}</option>
            </select>
            </div>

            <!-- <div>
            <label for="total-teams">Number of Teams</label>
            <input type="list" id="total-teams" name="total-teams" required>
            </div> -->

            <div>
            <label for="num-of-players">Max players on your team</label>
            <input placeholder="Select # of players" type="number" min="1" name="num-of-players" v-model="team.maxPlayers" required>
            </div>


            <div>
                <label for="accepting-teammates">Is your team accepting new teammates?</label>
                <div class="radio">
                    <label for="is-accepting-yes">True</label>
                    <input type="radio" id="is-accepting-yes" name="is-accepting" value="true" v-model="team.isAccepting">
                    <label for="is-accepting-no">False</label>
                    <input type="radio" id="is-accepting-no" name="is-accepting" value="false" v-model="team.isAccepting">
                </div>

            </div>


            <button type="submit" id="form-submit">SUBMIT TEAM!</button>
        </form>
    </div>
</template>
<script>
import axios from 'axios';
import GamesService from '../services/GamesService.js';
import TeamService from '../services/TeamService.js';


export default {

    name: 'TeamForm',
    data() {
        return {
            team: {
                teamName: '',
                gameId: '',
                maxPlayers: '',
                isAccepting: ''
            },
            games: [],
            selectedGameName: '',

        }
    },
    computed: {
        filteredGames() {
            if(!this.selectedGameName) {
                return this.games;
            }
            return this.games.filter(game => game.gameName.toLowerCase().includes(this.selectedGameName.toLowerCase()));
        }
    },

    methods: {
        
    submitTeam() {
        TeamService.createTeam(this.team)
            .then((response) => {
                console.log("Team created successfully", response);
            })
    },

    handleGameChange() {
        const game = this.games.find(game => game.gameName === this.selectedGameName);
        if (game) {
            this.team.gameId = game.gameId;
            this.isGameSelected = true;
            this.showDatalist = false;
        }
    },

    
    },

    created() { 
        GamesService.getAllGames().then((response) => {
            this.games = response.data;
        });
    },
}
</script>

<style>
#team-form-title {
    margin-bottom: 20px;
    color: #000000
}

.team-form {
    position: relative;
    width: 50%;
    text-align: center;
    padding: 40px;
    border: 1px solid rgb(124, 124, 124);
    border-radius: 10px;
    background-color: rgba(255, 255, 255, 0.6);
    color: #000000;
    font-weight: bold;
    margin-bottom: 20px;
}

form {
    display: flex;
    flex-direction: column;
    align-items: center;
}

label,
input,
select {
    width: 105%;
    margin-bottom: 10px;
}

select {
    height: 20px;
    width: 150px;
}

#form-submit {
    font-size: 17px;
    color: #B130FC;
    font-weight: bold;
    cursor: pointer;
}

.checkbox {
    display: flex;
    justify-content: center;
    align-items: center;
}

.radio {
    display: flex;
    justify-content: center;
    align-items: center;
}

#team-name {
    margin-bottom: 20px;
}

#num-of-players {
    margin-bottom: 20px;
}

#accepting-teammates {
    margin-bottom: 20px;
}

#preference {
    margin-bottom: 20px;
}


</style>