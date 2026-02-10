using Backend.Domain;

namespace Backend.Persistence.Entities;

public class SrbopolyContext : DbContext
{

    public DbSet<BoardEntity> Boards {get; set;}
    public DbSet<CardEntity> Cards {get; set;}
    public DbSet<FieldEntity> Fields {get; set;}
    public DbSet<GameEntity> Games {get; set;}
    public DbSet<PropertyFieldEntity> PropertyFields {get; set;}
    public DbSet<UserEntity> Users {get; set;}
    public DbSet<PlayerEntity> Players {get; set;}

    public SrbopolyContext(DbContextOptions options) : base(options) {}
}