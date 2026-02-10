using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Srbopoly_backend.Migrations
{
    /// <inheritdoc />
    public partial class FixingPlayerEntity : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Boards",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1")
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Boards", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Username = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: false),
                    Points = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "Games",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Status = table.Column<int>(type: "int", nullable: false),
                    MaxTurns = table.Column<int>(type: "int", nullable: false),
                    CurrentTurn = table.Column<int>(type: "int", nullable: false),
                    CurrentPlayerIndex = table.Column<int>(type: "int", nullable: false),
                    BoardID = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Games", x => x.ID);
                    table.ForeignKey(
                        name: "FK_Games_Boards_BoardID",
                        column: x => x.BoardID,
                        principalTable: "Boards",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateTable(
                name: "Cards",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    CardType = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    GameCardID = table.Column<int>(type: "int", nullable: false),
                    GameEntityID = table.Column<int>(type: "int", nullable: true),
                    GameEntityID1 = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Cards", x => x.ID);
                    table.ForeignKey(
                        name: "FK_Cards_Games_GameEntityID",
                        column: x => x.GameEntityID,
                        principalTable: "Games",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK_Cards_Games_GameEntityID1",
                        column: x => x.GameEntityID1,
                        principalTable: "Games",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateTable(
                name: "Players",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Balance = table.Column<int>(type: "int", nullable: false),
                    Position = table.Column<int>(type: "int", nullable: false),
                    Color = table.Column<int>(type: "int", nullable: false),
                    IsInJail = table.Column<bool>(type: "bit", nullable: false),
                    UserID = table.Column<int>(type: "int", nullable: true),
                    GameEntityID = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Players", x => x.ID);
                    table.ForeignKey(
                        name: "FK_Players_Games_GameEntityID",
                        column: x => x.GameEntityID,
                        principalTable: "Games",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK_Players_Users_UserID",
                        column: x => x.UserID,
                        principalTable: "Users",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateTable(
                name: "Fields",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    FieldType = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    GameFieldID = table.Column<int>(type: "int", nullable: false),
                    BoardEntityID = table.Column<int>(type: "int", nullable: true),
                    Discriminator = table.Column<string>(type: "nvarchar(21)", maxLength: 21, nullable: false),
                    OwnerID = table.Column<int>(type: "int", nullable: true),
                    Houses = table.Column<int>(type: "int", nullable: true),
                    Hotels = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Fields", x => x.ID);
                    table.ForeignKey(
                        name: "FK_Fields_Boards_BoardEntityID",
                        column: x => x.BoardEntityID,
                        principalTable: "Boards",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK_Fields_Players_OwnerID",
                        column: x => x.OwnerID,
                        principalTable: "Players",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateIndex(
                name: "IX_Cards_GameEntityID",
                table: "Cards",
                column: "GameEntityID");

            migrationBuilder.CreateIndex(
                name: "IX_Cards_GameEntityID1",
                table: "Cards",
                column: "GameEntityID1");

            migrationBuilder.CreateIndex(
                name: "IX_Fields_BoardEntityID",
                table: "Fields",
                column: "BoardEntityID");

            migrationBuilder.CreateIndex(
                name: "IX_Fields_OwnerID",
                table: "Fields",
                column: "OwnerID");

            migrationBuilder.CreateIndex(
                name: "IX_Games_BoardID",
                table: "Games",
                column: "BoardID");

            migrationBuilder.CreateIndex(
                name: "IX_Players_GameEntityID",
                table: "Players",
                column: "GameEntityID");

            migrationBuilder.CreateIndex(
                name: "IX_Players_UserID",
                table: "Players",
                column: "UserID");

            migrationBuilder.CreateIndex(
                name: "IX_Users_Username",
                table: "Users",
                column: "Username",
                unique: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Cards");

            migrationBuilder.DropTable(
                name: "Fields");

            migrationBuilder.DropTable(
                name: "Players");

            migrationBuilder.DropTable(
                name: "Games");

            migrationBuilder.DropTable(
                name: "Users");

            migrationBuilder.DropTable(
                name: "Boards");
        }
    }
}
