using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Srbopoly_backend.Migrations
{
    /// <inheritdoc />
    public partial class PlayerEntityMigration : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Fields_Boards_BoardEntityID",
                table: "Fields");

            migrationBuilder.DropForeignKey(
                name: "FK_Fields_Players_OwnerID",
                table: "Fields");

            migrationBuilder.DropForeignKey(
                name: "FK_Games_Boards_BoardID",
                table: "Games");

            migrationBuilder.DropForeignKey(
                name: "FK_Players_Users_UserID",
                table: "Players");

            migrationBuilder.DropPrimaryKey(
                name: "PK_Fields",
                table: "Fields");

            migrationBuilder.DropColumn(
                name: "Discriminator",
                table: "Fields");

            migrationBuilder.DropColumn(
                name: "FieldType",
                table: "Fields");

            migrationBuilder.RenameTable(
                name: "Fields",
                newName: "PropertyFields");

            migrationBuilder.RenameColumn(
                name: "UserID",
                table: "Players",
                newName: "UserId");

            migrationBuilder.RenameIndex(
                name: "IX_Players_UserID",
                table: "Players",
                newName: "IX_Players_UserId");

            migrationBuilder.RenameIndex(
                name: "IX_Fields_OwnerID",
                table: "PropertyFields",
                newName: "IX_PropertyFields_OwnerID");

            migrationBuilder.RenameIndex(
                name: "IX_Fields_BoardEntityID",
                table: "PropertyFields",
                newName: "IX_PropertyFields_BoardEntityID");

            migrationBuilder.AlterColumn<int>(
                name: "UserId",
                table: "Players",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "BoardID",
                table: "Games",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "CardType",
                table: "Cards",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "Houses",
                table: "PropertyFields",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "Hotels",
                table: "PropertyFields",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AddPrimaryKey(
                name: "PK_PropertyFields",
                table: "PropertyFields",
                column: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK_Games_Boards_BoardID",
                table: "Games",
                column: "BoardID",
                principalTable: "Boards",
                principalColumn: "ID",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Players_Users_UserId",
                table: "Players",
                column: "UserId",
                principalTable: "Users",
                principalColumn: "ID",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_PropertyFields_Boards_BoardEntityID",
                table: "PropertyFields",
                column: "BoardEntityID",
                principalTable: "Boards",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK_PropertyFields_Players_OwnerID",
                table: "PropertyFields",
                column: "OwnerID",
                principalTable: "Players",
                principalColumn: "ID");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Games_Boards_BoardID",
                table: "Games");

            migrationBuilder.DropForeignKey(
                name: "FK_Players_Users_UserId",
                table: "Players");

            migrationBuilder.DropForeignKey(
                name: "FK_PropertyFields_Boards_BoardEntityID",
                table: "PropertyFields");

            migrationBuilder.DropForeignKey(
                name: "FK_PropertyFields_Players_OwnerID",
                table: "PropertyFields");

            migrationBuilder.DropPrimaryKey(
                name: "PK_PropertyFields",
                table: "PropertyFields");

            migrationBuilder.RenameTable(
                name: "PropertyFields",
                newName: "Fields");

            migrationBuilder.RenameColumn(
                name: "UserId",
                table: "Players",
                newName: "UserID");

            migrationBuilder.RenameIndex(
                name: "IX_Players_UserId",
                table: "Players",
                newName: "IX_Players_UserID");

            migrationBuilder.RenameIndex(
                name: "IX_PropertyFields_OwnerID",
                table: "Fields",
                newName: "IX_Fields_OwnerID");

            migrationBuilder.RenameIndex(
                name: "IX_PropertyFields_BoardEntityID",
                table: "Fields",
                newName: "IX_Fields_BoardEntityID");

            migrationBuilder.AlterColumn<int>(
                name: "UserID",
                table: "Players",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<int>(
                name: "BoardID",
                table: "Games",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<string>(
                name: "CardType",
                table: "Cards",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<int>(
                name: "Houses",
                table: "Fields",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<int>(
                name: "Hotels",
                table: "Fields",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AddColumn<string>(
                name: "Discriminator",
                table: "Fields",
                type: "nvarchar(21)",
                maxLength: 21,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "FieldType",
                table: "Fields",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddPrimaryKey(
                name: "PK_Fields",
                table: "Fields",
                column: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK_Fields_Boards_BoardEntityID",
                table: "Fields",
                column: "BoardEntityID",
                principalTable: "Boards",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK_Fields_Players_OwnerID",
                table: "Fields",
                column: "OwnerID",
                principalTable: "Players",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK_Games_Boards_BoardID",
                table: "Games",
                column: "BoardID",
                principalTable: "Boards",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK_Players_Users_UserID",
                table: "Players",
                column: "UserID",
                principalTable: "Users",
                principalColumn: "ID");
        }
    }
}
