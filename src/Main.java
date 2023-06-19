public class Main {
  public static void main(String[] args) throws Exception {
    GameProgress gameProgress1 =
        new GameProgress(94, 10, 2, 254.32);
    GameProgress gameProgress2 =
        new GameProgress(94, 10, 2, 254.32);
    GameProgress gameProgress3 =
        new GameProgress(94, 10, 2, 254.32);

    gameProgress1.saveGame("/Users/alekseyogoltsov/Games/savegames/save1.dat");
    gameProgress2.saveGame("/Users/alekseyogoltsov/Games/savegames/save2.dat");
    gameProgress3.saveGame("/Users/alekseyogoltsov/Games/savegames/save3.dat");

    GameProgress.zipFiles(new String[]{
        "/Users/alekseyogoltsov/Games/savegames/save1.dat",
        "/Users/alekseyogoltsov/Games/savegames/save2.dat",
        "/Users/alekseyogoltsov/Games/savegames/save3.dat"
    }, "/Users/alekseyogoltsov/Games/savegames/save.zip");
  }
}