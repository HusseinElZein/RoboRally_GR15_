package HTTPClientAndServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerController {

    @Autowired
    private IRoboRallyService RoboRallyService;

    //host Game
    @PostMapping(value = "/game")
    public ResponseEntity<String> createGame(@RequestBody String str){
        String newServerID = RoboRallyService.hostServer(str);
        if (newServerID == null) //something went wrong
            return ResponseEntity.internalServerError().body("Server couldn't start");
        return ResponseEntity.ok().body(newServerID);
    }

    //listOfGame
    @GetMapping(value = "/game")
    public ResponseEntity<String> listOfGame(){
        return ResponseEntity.ok().body(RoboRallyService.listOfSavedGames());
    }

    //Join game
    @PutMapping(value = "/game/{id}")
    public ResponseEntity<String> joinGame(@PathVariable String id){
        String response = RoboRallyService.joinGame(id);
        if (response.equals("Server doesn't exist"))
            return ResponseEntity.status(404).body(response);

        return ResponseEntity.ok().body(response);

    }

    @GetMapping(value = "/gameState/{id}")
    public ResponseEntity<String> getGameState(@PathVariable String id)
    {
        return ResponseEntity.ok().body(RoboRallyService.getGameState(id));
    }

    @PutMapping(value = "/gameState/{id}")
    public ResponseEntity<String> setGameState(@PathVariable String id, @RequestBody String g)
    {
        RoboRallyService.updateGame(id,g);
        return ResponseEntity.ok().body("ok");
    }
}