using Backend.Persistence.Entities;
using Backend.Repositories;
using Backend.Repository;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddScoped<UserRepository>();
builder.Services.AddScoped<IGameRepository, GameRepository>();
builder.Services.AddScoped<CardRepository>();
builder.Services.AddScoped<BoardRepository>();
builder.Services.AddScoped<PlayerRepository>();
builder.Services.AddScoped<PropertyFieldRepository>();

builder.Services.AddControllers();
builder.Services.AddDbContext<SrbopolyContext> (Options => 
Options.UseSqlServer(builder.Configuration.GetConnectionString("SrbopolyCS")));

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.MapControllers();

app.Run();