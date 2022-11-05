class DrawClient {

  // TODO: add .env
  url = 'ws://localhost:8080/draw'
  private _connection?: WebSocket;

  connect(roomId: string) {
    if (!this._connection) {
      this._connection = new WebSocket(`${this.url}/${roomId}`);
    }

    this.init();

    return this._connection;
  }

  private init() {
    if (!this._connection) return;

    this._connection.onopen = () => {
      console.log("onopen");
    }
  }
}

export default new DrawClient();