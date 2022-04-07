# ![](./img/header.png)

#### [Figma Demo](https://www.figma.com/proto/K79ySLuqGCEd21DjGE6WXa/Game-Night-Mobile---Color?node-id=2%3A3&scaling=scale-down&page-id=0%3A1&starting-point-node-id=2%3A3)

## To Do

#### 🔴 High Priority

#### 🟠 Medium Priority
- [ ] overflow (3 dots) menu in GameView
- [ ] speed up AddGameManually search
- [ ] center text in NavBar
- [ ] make menu title smaller
- [ ] show inactive voting button as translucent (Jake)
- [ ] add "Delete Game Library" button to Settings

#### 🟡 Low Priority
- [ ] use APIs instead of local data (*partially implemented*)
- [ ] improve search result ordering in AddGameManually
- [ ] search, sort, and filter game library
- [ ] change FABs to Material2 style
- [ ] add background to menu bar?

#### 🟢 Completed
- [X] add AddGameManuallyActivity
    - [X] implement search feature
    - [X] add super sick custom loading animation
    - [X] tap game to add to library
    - [X] scroll to added game
- [X] add AddGameNightActivity
- [X] add GameLibraryActivity
    - [X] scale images for faster loading
    - [X] long press to delete game
- [X] add GameViewActivity
    - [X] tap game image show in full screen
    - [X] disable rules button if no URL found
- [X] add HomeActivity
  - [X] create card layout for Home
- [X] add SettingsActivity
- [X] implement barcode scanner
    - [X] add documentation on how to set up emulator to scan
    - [X] make barcode scanner look for games
- [X] loading spinner for AddGameManually search
- [X] speed up cache initialization
- [X] add GameNightsActivity
- [X] add GameNightActivity
	- [X] voting for games
- [X] fix clicking board game on game night card
- [X] fix context clicking on games on game night card
- [X] fix being able to add a game multiple times

#### ⚫️ Backlog
- [ ] make SettingsActivity functional
- [ ] create recurring game nights
- [ ] mark games as borrowable
- [ ] view other users' borrowable games
